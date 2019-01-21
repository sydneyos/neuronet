package com.weenet.neuronet.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.weenet.neuronet.api.NeuronetService
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.softwaremill.macwire._

class NeuronetLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new NeuronetApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new NeuronetApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[NeuronetService])
}

abstract class NeuronetApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[NeuronetService](wire[NeuronetServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = NeuronetSerializerRegistry

  // Register the Neuronet persistent entity
  persistentEntityRegistry.register(wire[NeuronetEntity])
}
