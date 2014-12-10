package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._

import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._

/**/
object globalErrors extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable, Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[Form[_$1] forSome {
  type _$1 >: _root_.scala.Nothing <: _root_.scala.Any
}, play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply /*1.2*/ (form: Form[_]): play.twirl.api.HtmlFormat.Appendable = {
    _display_ {

      Seq[Any](format.raw /*1.17*/ ("""

"""), _display_( /*3.2*/ if (form.hasGlobalErrors) /*3.26*/ {
        _display_(Seq[Any](format.raw /*3.28*/ ("""
    """), format.raw /*4.5*/ ("""<div class="has-error">
	    """), _display_( /*5.7*/ for (error <- form.globalErrors) yield /*5.39*/ {
          _display_(Seq[Any](format.raw /*5.41*/ ("""
	        """), format.raw /*6.10*/ ("""<span class="help-block">"""), _display_( /*6.36*/ error /*6.41*/ .message), format.raw /*6.49*/ ("""</span>
	    """)))
        }), format.raw /*7.7*/ ("""
    """), format.raw /*8.5*/ ("""</div>
""")))
      }))
    }
  }

  def render(form: Form[_$1] forSome {
               type _$1 >: _root_.scala.Nothing <: _root_.scala.Any
             }): play.twirl.api.HtmlFormat.Appendable = apply(form)

  def f: ((Form[_$1] forSome {
    type _$1 >: _root_.scala.Nothing <: _root_.scala.Any
  }) => play.twirl.api.HtmlFormat.Appendable) = (form) => apply(form)

  def ref: this.type = this

}
/*
                  -- GENERATED --
                  DATE: Mon Oct 27 13:30:11 UYST 2014
                  SOURCE: D:/dev/astropay/cardapp/cardappWeb/app/views/globalErrors.scala.html
                  HASH: 907cca9f590c64296f03acab9d5fc01cd93157f1
                  MATRIX: 586->1|689->16|717->19|749->43|788->45|819->50|874->80|921->112|960->114|998->125|1050->151|1063->156|1091->164|1134->178|1165->183
                  LINES: 21->1|24->1|26->3|26->3|26->3|27->4|28->5|28->5|28->5|29->6|29->6|29->6|29->6|30->7|31->8
                  -- GENERATED --
              */
