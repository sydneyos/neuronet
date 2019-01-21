package com.weenet.neuronetstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.weenet.neuronetstream.api.NeuronetStreamService
import com.weenet.neuronet.api.NeuronetService
import com.softwaremill.macwire._

class NeuronetStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new NeuronetStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new NeuronetStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[NeuronetStreamService])
}

abstract class NeuronetStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[NeuronetStreamService](wire[NeuronetStreamServiceImpl])

  // Bind the NeuronetService client
  lazy val neuronetService = serviceClient.implement[NeuronetService]
}
