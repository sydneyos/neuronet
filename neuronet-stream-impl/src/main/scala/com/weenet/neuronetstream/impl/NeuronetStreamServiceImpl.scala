package com.weenet.neuronetstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.weenet.neuronetstream.api.NeuronetStreamService
import com.weenet.neuronet.api.NeuronetService

import scala.concurrent.Future

/**
  * Implementation of the NeuronetStreamService.
  */
class NeuronetStreamServiceImpl(neuronetService: NeuronetService) extends NeuronetStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(neuronetService.hello(_).invoke()))
  }
}
