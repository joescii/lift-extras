package net.liftmodules.extras

import scala.xml._

import net.liftweb._
import common._
import http.{Factory, RenderOut}
import http.js._
import JsCmds._
import JE._
import json._
import util.CssSel
import util.Helpers._

object NoticeExtras extends NoticeExtras

trait NoticeExtras {

  def noticeHtml(msg: String): NodeSeq = LiftExtras.noticeHtmlHandler.vend.noticeHtml(msg)
  def warningHtml(msg: String): NodeSeq = LiftExtras.noticeHtmlHandler.vend.warningHtml(msg)
  def errorHtml(msg: String): NodeSeq = LiftExtras.noticeHtmlHandler.vend.errorHtml(msg)

  /**
    * Allows for the following to be used when building snippets that return NodeSeq.
    *
    * Usage:
    *
    *   for {
    *     user <- User.currentUser ?~ "You must be logged in to edit your profile."
    *   } yield ({
    *   ...
    *   }): NodeSeq
    */
  implicit protected def boxNodeSeqToNodeSeq(in: Box[NodeSeq]): NodeSeq = in match {
    case Full(ns) => ns
    case Failure(msg, _, _) => errorHtml(msg)
    case Empty => warningHtml("Empty")
  }

  /**
    * Allows for the following to be used when building snippets that return CssSel.
    *
    * Usage:
    *
    *   for {
    *     user <- User.currentUser ?~ "You must be logged in to edit your profile."
    *   } yield ({
    *   ...
    *   }): CssSel
    */
  implicit protected def boxCssSelToCssSel(in: Box[CssSel]): CssSel = in match {
    case Full(csssel) => csssel
    case Failure(msg, _, _) => "*" #> errorHtml(msg)
    case Empty => "*" #> warningHtml("Empty")
  }

  /**
    * Allows for the following to be used when building Comet functions that return RenderOut. Usage:
    *
    *   for {
    *     user <- User.currentUser ?~ "You must be logged in to edit your profile."
    *   } yield ({
    *   ...
    *   }): RenderOut
    */
  implicit protected def boxRenderOutToRenderOut(in: Box[RenderOut]): RenderOut = in match {
    case Full(ro) => ro
    case Failure(msg, _, _) => new RenderOut(errorHtml(msg))
    case Empty => new RenderOut(warningHtml("Empty"))
  }

  /**
    * Allows for the following to be used when building functions that return JsCmd. Usage:
    *
    *   for {
    *     user <- User.currentUser ?~ "You must be logged in to edit your profile."
    *   } yield ({
    *   ...
    *   }): JsCmd
    */
  implicit protected def boxJsCmdToJsCmd(in: Box[JsCmd]): JsCmd = in match {
    case Full(jscmd) => jscmd
    case Failure(msg, _, _) => JsNotice.error(msg).asJsCmd
    case Empty => JsNotice.warning("Empty").asJsCmd
  }

  /**
    * Allows for the following to be used when building functions that return JValue. Usage:
    *
    *   for {
    *     user <- User.currentUser ?~ "You must be logged in to edit your profile."
    *   } yield ({
    *   ...
    *   }): JValue
    */
  implicit protected def boxJValueToJValue(in: Box[JValue]): JValue = in match {
    case Full(jv) => jv
    case Failure(msg, _, _) => JsNotice.error(msg).asJValue
    case Empty => JsNotice.warning("Empty").asJValue
  }
}
