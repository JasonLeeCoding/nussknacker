package pl.touk.nussknacker.engine.compiledgraph

import cats.data.{NonEmptyList, Validated}
import pl.touk.nussknacker.engine.api.Context
import pl.touk.nussknacker.engine.api.context.ValidationContext
import pl.touk.nussknacker.engine.api.lazyy.{LazyContext, LazyValuesProvider}
import pl.touk.nussknacker.engine.api.typed.ClazzRef
import pl.touk.nussknacker.engine.api.typed.typing.TypingResult
import pl.touk.nussknacker.engine.compiledgraph
import pl.touk.nussknacker.engine.compiledgraph.evaluatedparam.TypedExpression

import scala.concurrent.Future
import scala.language.implicitConversions

object expression {

  trait Expression {

    def language: String
    
    def original: String

    def evaluate[T](ctx: Context, lazyValuesProvider: LazyValuesProvider): Future[ValueWithLazyContext[T]]
  }

  trait ExpressionParser {

    def languageId: String

    def parse(original: String, ctx: ValidationContext, expectedType: TypingResult):
      Validated[NonEmptyList[ExpressionParseError], TypedExpression]

    def parseWithoutContextValidation(original: String, expectedType: TypingResult): Validated[NonEmptyList[ExpressionParseError], compiledgraph.expression.Expression]

  }

  case class ExpressionParseError(message: String)

  case class ValueWithLazyContext[T](value: T, lazyContext: LazyContext)

}