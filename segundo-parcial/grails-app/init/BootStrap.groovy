import segundo.parcial.Divisa
import segundo.parcial.Role
import segundo.parcial.User
import segundo.parcial.UserRole

class BootStrap {

    def init = { servletContext ->
        new Divisa(imagen:'./images/us.png',nombre:'USD', valor:'1',convert:'false',ver:'2',disponible:'true').save()
        new Divisa(imagen:'./images/us.png',nombre:'USD', valor:'1',convert:'false',ver:'2',disponible:'true').save()
        new Divisa(imagen:'./images/us.png',nombre:'MXN', valor:'1',convert:'false',ver:'2',disponible:'true').save()
        User user = new User ("user","pass").save()
        Role admin = new Role("ROLE_ADMIN").save()
        UserRole.create(user,admin,true)
    }
    def destroy = {
    }
}
