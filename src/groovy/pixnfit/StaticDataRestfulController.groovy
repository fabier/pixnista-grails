package pixnfit

import grails.artefact.Artefact
import grails.rest.RestfulController
import grails.transaction.Transactional

/**
 * Cette classe est là pour contourner le bug https://jira.grails.org/browse/GRAILS-11892
 * JsonCollectionRenderer fails rendering response for Rest Controllers in Grails 2.4.4
 * Non corrigé en date du 1er février 2015
 *
 * Le hack est de transformer le resultat sous forme d'un tableau plutot que sous forme de liste...
 */
@Artefact("Controller")
@Transactional(readOnly = true)
class StaticDataRestfulController<T> extends RestfulController<T> {

    StaticDataRestfulController(Class<T> resource) {
        super(resource, false)
    }

    StaticDataRestfulController(Class<T> resource, boolean readOnly) {
        super(resource, readOnly)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond listAllResources(params).toArray(), model: [("${resourceName}Count".toString()): countResources()]
    }
}
