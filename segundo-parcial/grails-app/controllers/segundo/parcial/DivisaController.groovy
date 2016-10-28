package segundo.parcial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class DivisaController extends RestfulController{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Divisa.list(params), model:[divisaCount: Divisa.count()]
    }

    def show(Divisa divisa) {
        respond divisa
    }

    def create() {
        respond new Divisa(params)
    }

    @Transactional
    def save(Divisa divisa) {
        if (divisa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (divisa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond divisa.errors, view:'create'
            return
        }

        divisa.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'divisa.label', default: 'Divisa'), divisa.id])
                redirect divisa
            }
            '*' { respond divisa, [status: CREATED] }
        }
    }

    def edit(Divisa divisa) {
        respond divisa
    }

    @Transactional
    def update(Divisa divisa) {
        def divisaG=Divisa.get(divisa.id)
        if (divisaGuardada == null) {
            save(alumno)
            return
        }else{
            def versionMaxima = Divisa.withCriteria{
                projections{
                    max 'ver'
                }
            }
            versionMaxima+=1
            divisaGuardada.ver=versionMaxima
            new Divisa(imagen:divisaG.imagen,nombre:divisaG.nombre, valor:divisaG.valor,convert:divisaG.convert,version:divisaG.versionMaxima,enabled:divisaG.enabled)
        }

        if (divisa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond divisa.errors, view:'edit'
            return
        }
        divisa.save flush:true
    }

    @Transactional
    def delete(Divisa divisa) {

        if (divisa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        divisa.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'divisa.label', default: 'Divisa'), divisa.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'divisa.label', default: 'Divisa'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
