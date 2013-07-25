package com.abstractinventions.imageproxy

import dispatch._
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import unfiltered.netty.{ServerErrorResponse, async}
import unfiltered.request.{Params, GET, Path}
import unfiltered.response._


object ImageProxy extends async.Plan
with ServerErrorResponse {
  val logger = org.clapper.avsl.Logger(getClass)

  //extract the param
  object UrlParam extends Params.Extract("u", Params.first)

  def intent = {
    case req@GET(Path("/i")) =>
      logger.debug("GET /i")
      req match {
        case Params(UrlParam(imgUrl)) => {

          val fetchReq = url(imgUrl)
          for {
            resp <- Http(fetchReq)
          } {

            val hdrFunctions:Iterable[ResponseFunction[Any]] = mapAsScalaMapConverter(resp.getHeaders)
              .asScala.mapValues(_.asScala)
              .map {case (k,v) => ResponseHeader(k,v)}

            val responseBytes = ResponseBytes(resp.getResponseBodyAsBytes)

            // YUCK...
            // TODO : remove the mutable variable
            var rf = hdrFunctions.head
            hdrFunctions.tail.foreach(f => rf = rf ~> f)
            rf = rf ~> responseBytes

            req.respond(rf)
          }
        }

      }
  }


}
