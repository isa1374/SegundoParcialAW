package segundo.parcial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
@Secured(['ROLE_ADMIN'])
class DivisaController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def index() {
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

    def update(Divisa divisa) {
        def divisaG=Divisa.get(divisa.id);
        def versionMaxima = Divisa.withCriteria{
                projections{
                    max 'ver'
                }
            }
         if (divisa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (divisa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond divisa.errors, view:'edit'
            return
        }
       new Divisa(imagen:divisaG.imagen,nombre:divisaG.nombre, valor:divisaG.valor,convert:divisaG.convert,ver:divisaG.ver+1,disponible:divisaG.disponible).save()
        divisaG.disponible=false;
        divisaG.valor=divisa.valor;
        redirect action:"index", method:"GET"
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
