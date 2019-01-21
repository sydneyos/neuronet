package com.weenet.neuronet.impl

import com.weenet.neuronet.api
import com.weenet.neuronet.api.{NeuronetService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

/**
  * Implementation of the NeuronetService.
  */
class NeuronetServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends NeuronetService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the Neuronet entity for the given ID.
    val ref = persistentEntityRegistry.refFor[NeuronetEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the Neuronet entity for the given ID.
    val ref = persistentEntityRegistry.refFor[NeuronetEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(NeuronetEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[NeuronetEvent]): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
