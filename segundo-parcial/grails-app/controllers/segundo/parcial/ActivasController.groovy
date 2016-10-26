package segundo.parcial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON

@Transactional(readOnly = true)
class ActivasController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def consultar() {
        def verStr=Divisa.withCriteria{
                projections{
                    max 'ver'
                }
            }
        def disponib=params.enabled
        def divisas1=Divisa.where{
            ver==verStr 
            disponible==disponib
        }list()
        
        def valor=[
            datos: divisas1
        ]
        render valor as JSON
    }
}

