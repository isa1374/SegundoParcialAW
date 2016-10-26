package segundo.parcial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DivisaController {

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

        divisa.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'divisa.label', default: 'Divisa'), divisa.id])
                redirect divisa
            }
            '*'{ respond divisa, [status: OK] }
        }
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
