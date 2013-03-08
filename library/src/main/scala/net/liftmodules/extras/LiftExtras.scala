package net.liftmodules.extras

import snippet._

import scala.xml._

import net.liftweb._
import common._
import http._
import http.js._
import JsCmds._
import JE._
import json._
import util.CssSel
import util.Helpers._

object LiftExtras extends Factory {

  val defaultEmptyMsg = new FactoryMaker[String]("Unknown empty value") {}
  val noticeHtmlHandler = new FactoryMaker[HtmlHandler](BootstrapHtmlHandler) {}
  val noticeAsJValue = new FactoryMaker[LiftNotice => JValue](LiftNotice.noticeAsJValue _) {}
  val noticeAsJsCmd = new FactoryMaker[LiftNotice => JsCmd](BsNotices.noticeAsJsCmd _) {}
  val parseJsonFunc = new FactoryMaker[(String, JValue => JsCmd) => JsCmd](JsExtras.defaultParseJsonFunc _) {}
  val moduleNamespace = new FactoryMaker[Seq[String]](Seq("app", "views")) {}

  def init(): Unit = {
    BsNotices.init()
  }
}