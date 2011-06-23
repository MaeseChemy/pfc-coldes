package es.uc3m.coldes.utils.lang
{
	import mx.resources.IResourceManager;
	import mx.resources.ResourceBundle;
	
	public class ResourceLanguage
	{
		public function ResourceLanguage()
		{
		}
		
		public static function setResources(resourceManager:IResourceManager):void{
			
			/***********/		
			/**ESPAÑOL**/
			/***********/
			var myResources:ResourceBundle=new ResourceBundle("es_ES","myResources");
			myResources.content['ICON']="assets/lang/es.png";
			
			//MODULES NAMES
			myResources.content['MODULE.USERMANAGEMENT']="Gestión de usuarios";
			myResources.content['MODULE.ROOMMANAGEMENT']="Gestión de salas";
			
			//LOGIN
			myResources.content['LOGIN.TITLE']="Acceder a ColDes";
			myResources.content['LOGIN.USERNAME']="Usuario";
			myResources.content['LOGIN.PASSWORD']="Contraseña";
			myResources.content['LOGIN.REGISTER']="Registrar";
			myResources.content['LOGIN.ACCESS']="Acceder";
			
			//REGISTER
			myResources.content['REGISTER.TITLE']="Registro de usuario";
			myResources.content['REGISTER.USERNAME']="Usuario";
			myResources.content['REGISTER.PASSWORD']="Contraseña";
			myResources.content['REGISTER.PASSWORDCONFIRM']="Confirme Contraseña";
			myResources.content['REGISTER.NAME']="Nombre";
			myResources.content['REGISTER.SURNAME1']="Apellido 1";
			myResources.content['REGISTER.SURNAME2']="Apellido 2";
			myResources.content['REGISTER.EMAIL']="Correo electronico";
			myResources.content['REGISTER.CANCEL']="Cancelar";
			myResources.content['REGISTER.SUBMIT']="Registrar";
			myResources.content['REGISTER.TITLEUPDATE']="Datos de usuario";
			myResources.content['REGISTER.SUBMITUPDATE']="Actualizar";
			myResources.content['REGISTER.CHANGEPASSCHECK']="Cambiar Contraseña";
			myResources.content['REGISTER.ADMIN']="Administrador";
			myResources.content['REGISTER.DESIGNER']="Diseñador";
			myResources.content['REGISTER.ACTIVE']="Activo";
			
			//MAIN WINDOW
			myResources.content['MAINWINDOW.LOGOUT']="Salir";
			myResources.content['MAINWINDOW.TITLE']="Inicio";
			myResources.content['MAINWINDOW.USERS']="Usuarios";
			myResources.content['MAINWINDOW.ROOMS']="Salas";
			myResources.content['MAINWINDOW.ADMINISTRATOR']="Administración";
			myResources.content['MAINWINDOW.MYDATA']="Mis Datos";
			myResources.content['MAINWINDOW.MYROOMS']="Mis Salas";
			myResources.content['MAINWINDOW.SEARCHROOMS']="Buscar Salas";
			myResources.content['MAINWINDOW.CREATEROOM']="Crear Sala";
			myResources.content['MAINWINDOW.USERMANAGEMENT']="Administrar Usuarios";
			myResources.content['MAINWINDOW.ROOMMANAGEMENT']="Administrar Salas";
			
			//MY ROOMS
			myResources.content['MYROOMS.CREATEROOM']="Crear Sala";
			myResources.content['MYROOMS.SEARCH']="Búsqueda";
			myResources.content['MYROOMS.RESTART']="Restaurar";
			myResources.content['MYROOMS.SEARCHBTN']="Buscar";
			myResources.content['MYROOMS.ROOMNAME']="Nombre de la Sala";
			myResources.content['MYROOMS.OWNERUSERNAME']="Propietario";
			myResources.content['MYROOMS.USER']="Usuario";
			myResources.content['MYROOMS.ROL']="Rol";
			
			//USER MANAGEMENT
			myResources.content['USERMANAGEMENT.SEARCH']="Búsqueda";
			myResources.content['USERMANAGEMENT.RESTART']="Restaurar";
			myResources.content['USERMANAGEMENT.SEARCHBTN']="Buscar";
			myResources.content['USERMANAGEMENT.USER']="Usuario";
			myResources.content['USERMANAGEMENT.NAME']="Nombre";
			myResources.content['USERMANAGEMENT.SURNAME1']="Apellido 1";
			myResources.content['USERMANAGEMENT.SURNAME2']="Apellido 2";
			
			
			//MESSAGE's
			myResources.content['MESSAGETITLE.REGISTER']="REGISTRO COMPLETO";
			myResources.content['MESSAGE.REGISTER']="El registro del nuevo usuario se realizo satisfactoriamente.";
			myResources.content['MESSAGETITLE.UPDATE']="ACTUALIZACIÓN COMPLETO";
			myResources.content['MESSAGE.UPDATE']="Actualización del usuario realizada con éxito.";
			myResources.content['MESSAGETITLE.REGISTERROOM']="REGISTRO DE SALA COMPLETO";
			myResources.content['MESSAGE.REGISTERROOM']="La creación de la nueva sala se realizo correctamente.";
			
			//ERROR MESSAGE's
			myResources.content['ERRORTITLE.LOGIN']="USUARIO INEXISTENTE";
			myResources.content['ERROR.LOGIN']="El usuario con el que esta intentando acceder es erroneo, por favor verifique los campos.";
			myResources.content['ERRORTITLE.REGISTER']="USUARIO EXISTENTE";
			myResources.content['ERROR.REGISTER']="El usuario ya existe.";
			myResources.content['ERRORTITLE.REGISTERFORM']="ERRORES EN EL FORMULARIO DE REGISTRO";
			myResources.content['ERROR.REGISTERFORM']="Existen errores en el formulario de registro, verifique los campos.";
			myResources.content['ERRORTITLE.REGISTERROOM']="SALA EXISTENTE";
			myResources.content['ERROR.REGISTERROOM']="La sala que desea crear ya existe.";
			
			//ERROR VALIDATORS's
			myResources.content['VALIDATORTYPE.PASSWORD']="Password Incorrecta";
			myResources.content['VALIDATOR.PASSWORD']="Las contraseñas deben coincidir.";
			myResources.content['ERRORTITLE.SERVER']="ERROR INTERNO";
			myResources.content['ERROR.SERVER']="Se produjo un error al realizar la operación, consulte con el administrador de la aplicación.";
			
			resourceManager.addResourceBundle(myResources);
			
			/**********/		
			/**INGLES**/
			/**********/
			myResources=new ResourceBundle("en_US","myResources");
			myResources.content['ICON']="assets/lang/us.png";
			
			//MODULES NAMES
			myResources.content['MODULE.USERMANAGEMENT']="User Management";
			myResources.content['MODULE.ROOMMANAGEMENT']="Room Management";
			
			//LOGIN
			myResources.content['LOGIN.TITLE']="ColDesign Login";
			myResources.content['LOGIN.USERNAME']="User";
			myResources.content['LOGIN.PASSWORD']="Password";
			myResources.content['LOGIN.REGISTER']="Register";
			myResources.content['LOGIN.ACCESS']="Login";
			
			//REGISTER
			myResources.content['REGISTER.TITLE']="User register";
			myResources.content['REGISTER.USERNAME']="User";
			myResources.content['REGISTER.PASSWORD']="Password";
			myResources.content['REGISTER.PASSWORDCONFIRM']="Confirm Password";
			myResources.content['REGISTER.NAME']="Name";
			myResources.content['REGISTER.SURNAME1']="Surname 1";
			myResources.content['REGISTER.SURNAME2']="Surname 2";
			myResources.content['REGISTER.EMAIL']="e-mail";
			myResources.content['REGISTER.CANCEL']="Cancel";
			myResources.content['REGISTER.SUBMIT']="Submit";
			myResources.content['REGISTER.TITLEUPDATE']="User data";
			myResources.content['REGISTER.SUBMITUPDATE']="Update";
			myResources.content['REGISTER.CHANGEPASSCHECK']="Change Password";
			myResources.content['REGISTER.ADMIN']="Administrator";
			myResources.content['REGISTER.DESIGNER']="Designer";
			myResources.content['REGISTER.ACTIVE']="Active";
			
			//MAIN WINDOW
			myResources.content['MAINWINDOW.LOGOUT']="Logout";
			myResources.content['MAINWINDOW.TITLE']="Home";
			myResources.content['MAINWINDOW.USERS']="Users";
			myResources.content['MAINWINDOW.ROOMS']="Rooms";
			myResources.content['MAINWINDOW.ADMINISTRATOR']="Administration";
			myResources.content['MAINWINDOW.MYDATA']="My Data";
			myResources.content['MAINWINDOW.MYROOMS']="My Rooms";
			myResources.content['MAINWINDOW.SEARCHROOMS']="Search Room";
			myResources.content['MAINWINDOW.CREATEROOM']="Create Room";
			myResources.content['MAINWINDOW.USERMANAGEMENT']="User Management";
			myResources.content['MAINWINDOW.ROOMMANAGEMENT']="Room Management";
			
			//MY ROOMS
			myResources.content['MYROOMS.CREATEROOM']="Create Room";
			myResources.content['MYROOMS.SEARCH']="Search";
			myResources.content['MYROOMS.RESTART']="Restart";
			myResources.content['MYROOMS.SEARCHBTN']="Search";
			myResources.content['MYROOMS.ROOMNAME']="Room Name";
			myResources.content['MYROOMS.OWNERUSERNAME']="Owner";
			myResources.content['MYROOMS.USER']="Username";
			myResources.content['MYROOMS.ROL']="Rol";
			
			//USER MANAGEMENT
			myResources.content['USERMANAGEMENT.SEARCH']="Search";
			myResources.content['USERMANAGEMENT.RESTART']="Restart";
			myResources.content['USERMANAGEMENT.SEARCHBTN']="Search";
			myResources.content['USERMANAGEMENT.USER']="Username";
			myResources.content['USERMANAGEMENT.NAME']="Name";
			myResources.content['USERMANAGEMENT.SURNAME1']="Surname 1";
			myResources.content['USERMANAGEMENT.SURNAME2']="Surname 2";
			
			
			//MESSAGE's
			myResources.content['MESSAGETITLE.REGISTER']="REGISTER COMPLETE";
			myResources.content['MESSAGE.REGISTER']="User register successful.";
			myResources.content['MESSAGETITLE.UPDATE']="UPDATE COMPLETE";
			myResources.content['MESSAGE.UPDATE']="User update completed successfully.";
			myResources.content['MESSAGETITLE.REGISTERROOM']="ROOM REGISTER COMPLETE";
			myResources.content['MESSAGE.REGISTERROOM']="Room register successful.";
			
			//ERROR MESSAGE's
			myResources.content['ERRORTITLE.LOGIN']="WRONG USER";
			myResources.content['ERROR.LOGIN']="The user you are trying to access is wrong, please check the fields.";
			myResources.content['ERRORTITLE.REGISTER']="REGISTER USER";
			myResources.content['ERROR.REGISTER']="The user already exist.";
			myResources.content['ERRORTITLE.REGISTERFORM']="REGISTER FORM ERROR's";
			myResources.content['ERROR.REGISTERFORM']="There are errors in the registration form, check the form fields.";
			myResources.content['ERRORTITLE.REGISTERROOM']="REGISTER ROOM";
			myResources.content['ERROR.REGISTERROOM']="The room already exist in your rooms.";
			
			//ERROR VALIDATORS's
			myResources.content['VALIDATORTYPE.PASSWORD']="Incorrect Password";
			myResources.content['VALIDATOR.PASSWORD']="Passwords must match.";
			myResources.content['ERRORTITLE.SERVER']="INTERNAL ERROR";
			myResources.content['ERROR.SERVER']="There was an error performing the operation, contact your application administrator.";
			
			resourceManager.addResourceBundle(myResources);		
			
			
			/****************************************************/
			resourceManager.update();
		}
	}
}