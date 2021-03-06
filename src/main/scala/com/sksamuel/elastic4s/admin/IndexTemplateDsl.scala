package com.sksamuel.elastic4s.admin

import com.sksamuel.elastic4s.ProxyClients
import com.sksamuel.elastic4s.mappings.MappingDefinition
import org.elasticsearch.action.admin.indices.template.delete.{ DeleteIndexTemplateRequest, DeleteIndexTemplateRequestBuilder }
import org.elasticsearch.action.admin.indices.template.put.{ PutIndexTemplateRequest, PutIndexTemplateRequestBuilder }

import scala.collection.mutable.ListBuffer

trait TemplateDsl {
  class CreateIndexTemplateExpectsPattern(name: String) {
    def pattern(pat: String) = new CreateIndexTemplateDefinition(name, pat)
  }
}

class CreateIndexTemplateDefinition(name: String, pattern: String) {

  val _mappings = new ListBuffer[MappingDefinition]
  val _builder = new PutIndexTemplateRequestBuilder(ProxyClients.indices, name).setTemplate(pattern)

  def build: PutIndexTemplateRequest = _builder.request

  def mappings(mappings: MappingDefinition*): this.type = {
    for (mapping <- mappings) {
      _builder.addMapping("", mapping.build)
    }
    this
  }
}

class DeleteIndexTemplateDefinition(name: String) {
  def build: DeleteIndexTemplateRequest = _builder.request
  val _builder = new DeleteIndexTemplateRequestBuilder(ProxyClients.indices, name)
}
