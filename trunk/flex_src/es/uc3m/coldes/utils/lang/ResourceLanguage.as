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
			
			
			//MESSAGE's
			myResources.content['MESSAGETITLE.REGISTER']="REGISTRO COMPLETO";
			myResources.content['MESSAGE.REGISTER']="El registro del nuevo usuario se realizo satisfactoriamente.";
			//ERROR MESSAGE's
			myResources.content['ERRORTITLE.LOGIN']="USUARIO INEXISTENTE";
			myResources.content['ERROR.LOGIN']="El usuario con el que esta intentando acceder es erroneo, por favor verifique los campos.";
			myResources.content['ERRORTITLE.REGISTER']="USUARIO EXISTENTE";
			myResources.content['ERROR.REGISTER']="El usuario ya existe.";
			myResources.content['ERRORTITLE.REGISTERFORM']="ERRORES EN EL FORMULARIO DE REGISTRO";
			myResources.content['ERROR.REGISTERFORM']="Existen errores en el formulario de registro, verifique los campos.";
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
			
			//MESSAGE's
			myResources.content['MESSAGETITLE.REGISTER']="REGISTER COMPLETE";
			myResources.content['MESSAGE.REGISTER']="User register successful.";
			//ERROR MESSAGE's
			myResources.content['ERRORTITLE.LOGIN']="WRONG USER";
			myResources.content['ERROR.LOGIN']="The user you are trying to access is wrong, please check the fields.";
			myResources.content['ERRORTITLE.REGISTER']="REGISTER USER";
			myResources.content['ERROR.REGISTER']="The user already exist.";
			myResources.content['ERRORTITLE.REGISTERFORM']="REGISTER FORM ERROR's";
			myResources.content['ERROR.REGISTERFORM']="There are errors in the registration form, check the form fields.";
			
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