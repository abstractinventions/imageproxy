package com.abstractinventions.imageproxy

import com.abstractinventions.imageproxy.ImageProxy

/** embedded server */
object Server {
  val logger = org.clapper.avsl.Logger(Server.getClass)

  def main(args: Array[String]) {
    val port = args(0).toInt
    unfiltered.netty.Http(port)
      .handler(ImageProxy)
      .run { s =>
        logger.info("starting at localhost on port %s"
                    .format(s.port))
      }
  }
}
