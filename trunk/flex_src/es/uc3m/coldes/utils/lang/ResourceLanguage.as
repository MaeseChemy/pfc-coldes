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
			
/****************************************************************************************************************/		
/*******************************************************ESPAÑOL**************************************************/
/****************************************************************************************************************/

			var myResources:ResourceBundle=new ResourceBundle("es_ES","myResources");
			myResources.content['ICON']="assets/lang/es.png";
			
			//WELLCOME
			myResources.content['APPTITLE.WELLCOME']="Bienvenido a ColDes";
			myResources.content['APPTITLE.MAIN']="ColDes";
			
			//MODULES NAMES
			myResources.content['MODULE.USERMANAGEMENT']="Gestión de usuarios";
			myResources.content['MODULE.ROOMMANAGEMENT']="Gestión de salas";
			
			//ROLNAMES
			myResources.content['ROLNAME.DEFAULT']="Defecto";
			myResources.content['ROLNAME.OWNER']="Propietario";
			myResources.content['ROLNAME.MODERATOR']="Moderador";
			myResources.content['ROLNAME.COLABORATOR']="Colaborador";
			myResources.content['ROLNAME.GUEST']="Invitado";
			
			//LOGIN
			myResources.content['LOGIN.TITLE']="Acceder a ColDes";
			myResources.content['LOGIN.USERNAME']="Usuario";
			myResources.content['LOGIN.PASSWORD']="Contraseña";
			myResources.content['LOGIN.REGISTER']="Registrar";
			myResources.content['LOGIN.ACCESS']="Acceder";
			
			//REGISTER
			myResources.content['REGISTER.TITLE']="Registro de usuario";
			myResources.content['REGISTER.TITLEROOM']="Registro de sala";
			myResources.content['REGISTER.TITLEDESING']="Registro de diseño";
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
			myResources.content['REGISTER.TITLEROOMUPDATE']="Datos de la sala";
			myResources.content['REGISTER.TITLEDESINGUPDATE']="Datos del diseño";
			myResources.content['REGISTER.CHANGEPASSCHECK']="Cambiar Contraseña";
			myResources.content['REGISTER.ADMIN']="Administrador";
			myResources.content['REGISTER.DESIGNER']="Diseñador";
			myResources.content['REGISTER.ACTIVE']="Activo";
			myResources.content['REGISTER.PRIVATE']="Privada";
			myResources.content['REGISTER.PARTICIPATIONTYPE']="Tipo de participación";
			myResources.content['REGISTER.DESCRIPTION']="Descripción";
			myResources.content['REGISTER.ONEPAINTING']="Uno a Uno";
			myResources.content['REGISTER.ALLPAITING']="Todos a la Vez";
			myResources.content['REGISTER.ROL']="Rol";
			
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
			myResources.content['MYROOMS.ROOM']="Sala";
			myResources.content['MYROOMS.USER']="Usuario";
			myResources.content['MYROOMS.USERS']="Usuarios";
			myResources.content['MYROOMS.ROL']="Rol";
			myResources.content['MYROOMS.EDITROOM']="Editar Sala";
			myResources.content['MYROOMS.REMOVE']="Eliminar relación";
			
			//SEARCH ROOMS
			myResources.content['SEARCHROOM.DESCRIPTION']="Descripción";
			
			//USER MANAGEMENT
			myResources.content['USERMANAGEMENT.SEARCH']="Búsqueda";
			myResources.content['USERMANAGEMENT.RESTART']="Restaurar";
			myResources.content['USERMANAGEMENT.SEARCHBTN']="Buscar";
			myResources.content['USERMANAGEMENT.USER']="Usuario";
			myResources.content['USERMANAGEMENT.NAME']="Nombre";
			myResources.content['USERMANAGEMENT.SURNAME1']="Apellido 1";
			myResources.content['USERMANAGEMENT.SURNAME2']="Apellido 2";
		
			//MANAGE USER DESIGNS
			myResources.content['MANAGEUSERDESIGNS.TITLE']="Mis diseños";
			myResources.content['MANAGEUSERDESIGNS.DESIGNS']="Diseños";
			myResources.content['MANAGEUSERDESIGNS.TOOLTIPREMOVE']="Borar diseño";
			
			//SAVE USER DESIGNS
			myResources.content['SAVEUSERDESIGN.TITLE']="Salvar en mis diseños";
		
			//CHAT
			myResources.content['CHAT.USERS']="Usuarios";
			myResources.content['CHAT.SEND']="Enviar";
			
			//ROOMVIEW
			myResources.content['ROOMVIEW.USERENTER']="ha entrado en la sala";
			myResources.content['ROOMVIEW.USEREXIT']="ha salido de la sala";
			myResources.content['ROOMVIEW.PENCILREQUEST']="ha solicitado el pincel";
			myResources.content['ROOMVIEW.PENCILLEFT']="ha dejado el pincel";
			myResources.content['ROOMVIEW.PENCILOWNER']="es el nuevo propietario del pincel";
			myResources.content['ROOMVIEW.SQUARE']="Cuadrado";
			myResources.content['ROOMVIEW.CIRCLE']="Circulo";
			myResources.content['ROOMVIEW.STRAIGHT']="Recta";
			myResources.content['ROOMVIEW.FREEHAND']="Libre";
			myResources.content['ROOMVIEW.COLOR']="Color trazo";
			myResources.content['ROOMVIEW.FILLCOLOR']="Color relleno";
			myResources.content['ROOMVIEW.ACTFILL']="Activar relleno";
			myResources.content['ROOMVIEW.BULK']="Grosor";
			myResources.content['ROOMVIEW.SAVETOPC']="Exportar a PC";
			myResources.content['ROOMVIEW.UPLOADFROMPC']="Importar de PC";
			myResources.content['ROOMVIEW.CLEAN']="Limpiar lienzo";
			myResources.content['ROOMVIEW.UNDO']="Deshacer";
			myResources.content['ROOMVIEW.REDO']="Rehacer";
			myResources.content['ROOMVIEW.PLAY']="Reproducir";
			myResources.content['ROOMVIEW.UPLOADFROMDB']="Importar de mis diseños";
			myResources.content['ROOMVIEW.SAVETODB']="Guardar en mis diseños";
			myResources.content['ROOMVIEW.STOPSYNCRONIZE']="Sincronizar lienzo";
			myResources.content['ROOMVIEW.STARTSYNCRONIZE']="Desincronizar lienzo";
			myResources.content['ROOMVIEW.REQUESTPENCIL']="Solicitar pincel";
			myResources.content['ROOMVIEW.LEFTPENCIL']="Dejar pincel";
			myResources.content['ROOMVIEW.SAVECANVASTOROOM']="Asociar diseño a la sala";
			myResources.content['ROOMVIEW.REMOVECANVASFROMROOM']="Eliminar diseño de la sala";
			
			//MANAGE USERS ROOM
			myResources.content['MANAGEUSERROOMS.SENDINVITATION']="Enviar Invitación";
			myResources.content['MANAGEUSERROOMS.USERSOFCOLDES']="Usuarios de ColDes";
			myResources.content['MANAGEUSERROOMS.ROLEDIT']="Editar Rol";
			myResources.content['MANAGEUSERROOMS.DELETEUSER']="Eliminar Usuario";
			myResources.content['MANAGEUSERROOMS.USERSOFROOM']="Usuarios de la Sala";
			myResources.content['MANAGEUSERROOMS.ROL']="Rol";

			//MANAGE USER MESSAGES
			myResources.content['MESSAGESMANAGEMENT.ACCEPT']="Aceptar";
			myResources.content['MESSAGESMANAGEMENT.DECLINE']="Rechazar";
			myResources.content['MESSAGESMANAGEMENT.INVITATION']="Invitación";
			myResources.content['MESSAGESMANAGEMENT.INVITATIONTEXT1']="Invitación para la sala [";
			myResources.content['MESSAGESMANAGEMENT.INVITATIONTEXT2']="] del usuario (";
			myResources.content['MESSAGESMANAGEMENT.INVITATIONTEXT3']=") como \"";

			//POPUP ANSWERS
			myResources.content['ANSWERS.YES']="Si";
			myResources.content['ANSWERS.NO']="No";
			myResources.content['ANSWERS.ACCEPT']="Aceptar";
			myResources.content['ANSWERS.ENTERANDREGISTER']="Agregar y Entrar";
			myResources.content['ANSWERS.ONLYREGISTER']="Agregar solo";
			myResources.content['ANSWERS.CANCEL']="Cancelar";
			
			
			//MESSAGE's
			myResources.content['MESSAGETITLE.LOGOUT']="SALIR";
			myResources.content['MESSAGE.LOGOUT']="¿Desea salir?";
			myResources.content['MESSAGETITLE.REGISTER']="REGISTRO COMPLETO";
			myResources.content['MESSAGE.REGISTER']="El registro del nuevo usuario se realizo satisfactoriamente.";
			myResources.content['MESSAGETITLE.UPDATE']="ACTUALIZACIÓN COMPLETO";
			myResources.content['MESSAGE.UPDATE']="Actualización del usuario realizada con éxito.";
			myResources.content['MESSAGETITLE.REGISTERROOM']="REGISTRO DE SALA COMPLETO";
			myResources.content['MESSAGE.REGISTERROOM']="La creación de la nueva sala se realizo correctamente.";
			myResources.content['MESSAGETITLE.UPDATERROOM']="ACTUALIZACIÓN DE SALA COMPLETO";
			myResources.content['MESSAGE.UPDATERROOM']="La actualización de la sala se realizo correctamente.";
			myResources.content['MESSAGETITLE.REMOVEUSERRELATIONOWNER']="ELIMINAR RELACION Y SALA";
			myResources.content['MESSAGE.REMOVEUSERRELATIONOWNER']="Al ser el propietario de la sala, borrar la relación " + 
					"implicará borrar la sala y con ello todas las relaciones asociadas. ¿Esta seguro?";
			myResources.content['MESSAGETITLE.REMOVEUSERRELATION']="ELIMINAR RELACION";
			myResources.content['MESSAGE.REMOVEUSERRELATION']="¿Esta seguro que quiere borrarse de esta sala?";
			myResources.content['MESSAGETITLE.USERROOMREGISTER']="OPCIONES DE REGISTRO EN SALA";
			myResources.content['MESSAGE.USERROOMREGISTER']="¿Qué acción desea realizar?";
			myResources.content['MESSAGETITLE.SAVEDESIGN']="GUARDADO DE CONTENIDO";
			myResources.content['MESSAGE.SAVEDESIGNTOUSER']="Se ha asociado con éxito el diseño al usuario";
			myResources.content['MESSAGE.SAVEDESIGNTOCANVAS']="Se ha asociado con éxito el diseño a la sala";
			myResources.content['MESSAGETITLE.REMOVEDESIGN']="BORRADO DE CONTENIDO";
			myResources.content['MESSAGE.REMOVEDESIGN']="Se ha borrado con éxito el diseño asociado a la sala.";
			myResources.content['MESSAGETITLE.SENDINVITATION']="INVITACIÓN ENVIADA";
			myResources.content['MESSAGE.SENDINVITATION']="La invitación se envió correctamente al usuario.";
			myResources.content['MESSAGETITLE.INVITATIONTYPE']="TIPO DE INVITACIÓN";
			myResources.content['MESSAGE.INVITATIONTYPE']="¿Con que rol quiere invitar al usuario?";
			
			//ERROR MESSAGE's
			myResources.content['ERRORTITLE.LOGIN']="USUARIO INEXISTENTE";
			myResources.content['ERROR.LOGIN']="El usuario con el que esta intentando acceder es erroneo, por favor verifique los campos.";
			myResources.content['ERRORTITLE.REGISTER']="USUARIO EXISTENTE";
			myResources.content['ERROR.REGISTER']="El usuario ya existe.";
			myResources.content['ERRORTITLE.REGISTERFORM']="ERRORES EN EL FORMULARIO DE REGISTRO";
			myResources.content['ERROR.REGISTERFORM']="Existen errores en el formulario de registro, verifique los campos.";
			myResources.content['ERRORTITLE.REGISTERROOM']="SALA EXISTENTE";
			myResources.content['ERROR.REGISTERROOM']="La sala que desea crear ya existe.";
			myResources.content['ERRORTITLE.SAVEDESIGN']="ERROR EN GUARDADO";
			myResources.content['ERROR.SAVEDESIGN']="Se produjo un error al asociar el contenido del lienzo";


			//ERROR VALIDATORS's
			myResources.content['VALIDATORTYPE.PASSWORD']="Password Incorrecta";
			myResources.content['VALIDATOR.PASSWORD']="Las contraseñas deben coincidir.";
			myResources.content['ERRORTITLE.SERVER']="ERROR INTERNO";
			myResources.content['ERROR.SERVER']="Se produjo un error al realizar la operación, consulte con el administrador de la aplicación.";
			
			resourceManager.addResourceBundle(myResources);
			
/****************************************************************************************************************/		
/*******************************************************INGLES***************************************************/
/****************************************************************************************************************/

			myResources=new ResourceBundle("en_US","myResources");
			myResources.content['ICON']="assets/lang/us.png";
			
			//WELLCOME
			myResources.content['APPTITLE.WELLCOME']="Wellcome to ColDes";
			myResources.content['APPTITLE.MAIN']="ColDes";
			
			//MODULES NAMES
			myResources.content['MODULE.USERMANAGEMENT']="User Management";
			myResources.content['MODULE.ROOMMANAGEMENT']="Room Management";
			
			//ROLNAMES
			myResources.content['ROLNAME.DEFAULT']="Default";
			myResources.content['ROLNAME.OWNER']="Owner";
			myResources.content['ROLNAME.MODERATOR']="Moderator";
			myResources.content['ROLNAME.COLABORATOR']="Colaborator";
			myResources.content['ROLNAME.GUEST']="Guest";
			
			//LOGIN
			myResources.content['LOGIN.TITLE']="ColDesign Login";
			myResources.content['LOGIN.USERNAME']="User";
			myResources.content['LOGIN.PASSWORD']="Password";
			myResources.content['LOGIN.REGISTER']="Register";
			myResources.content['LOGIN.ACCESS']="Login";
			
			//REGISTER
			myResources.content['REGISTER.TITLE']="User register";
			myResources.content['REGISTER.TITLEROOM']="Room register";
			myResources.content['REGISTER.TITLEDESING']="Design register";
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
			myResources.content['REGISTER.TITLEROOMUPDATE']="Room data";
			myResources.content['REGISTER.TITLEDESINGUPDATE']="Design data";
			myResources.content['REGISTER.SUBMITUPDATE']="Update";
			myResources.content['REGISTER.CHANGEPASSCHECK']="Change Password";
			myResources.content['REGISTER.ADMIN']="Administrator";
			myResources.content['REGISTER.DESIGNER']="Designer";
			myResources.content['REGISTER.ACTIVE']="Active";
			myResources.content['REGISTER.PRIVATE']="Private";
			myResources.content['REGISTER.PARTICIPATIONTYPE']="Type of Participation";
			myResources.content['REGISTER.DESCRIPTION']="Description";
			myResources.content['REGISTER.ONEPAINTING']="One to One";
			myResources.content['REGISTER.ALLPAITING']="All at Once";
			myResources.content['REGISTER.ROL']="Rol";
			
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
			myResources.content['MYROOMS.ROOM']="Room";
			myResources.content['MYROOMS.USER']="Username";
			myResources.content['MYROOMS.USERS']="Users";
			myResources.content['MYROOMS.ROL']="Rol";
			myResources.content['MYROOMS.EDITROOM']="Edit room";
			myResources.content['MYROOMS.REMOVE']="Delete room relation";
			
			//SEARCH ROOMS
			myResources.content['SEARCHROOM.DESCRIPTION']="Description";
			
			//USER MANAGEMENT
			myResources.content['USERMANAGEMENT.SEARCH']="Search";
			myResources.content['USERMANAGEMENT.RESTART']="Restart";
			myResources.content['USERMANAGEMENT.SEARCHBTN']="Search";
			myResources.content['USERMANAGEMENT.USER']="Username";
			myResources.content['USERMANAGEMENT.NAME']="Name";
			myResources.content['USERMANAGEMENT.SURNAME1']="Surname 1";
			myResources.content['USERMANAGEMENT.SURNAME2']="Surname 2";
			
			//MANAGE USER DESIGNS
			myResources.content['SAVEUSERDESIGN.TITLE']="My Designs";
			myResources.content['MANAGEUSERDESIGNS.DESIGNS']="Designs";
			myResources.content['MANAGEUSERDESIGNS.TOOLTIPREMOVE']="Remove design";
			
			//SAVE USER DESIGNS
			myResources.content['SAVEUSERDESIGN.TITLE']="Save in my Design";
			
			//CHAT
			myResources.content['CHAT.USERS']="Users";
			myResources.content['CHAT.SEND']="Send";
			
			//ROOMVIEW
			myResources.content['ROOMVIEW.USERENTER']="has entered in the room";
			myResources.content['ROOMVIEW.USEREXIT']="has left the room";
			myResources.content['ROOMVIEW.PENCILREQUEST']= "has requested the pencil";
			myResources.content['ROOMVIEW.PENCILLEFT']="has left the pencil";
			myResources.content['ROOMVIEW.PENCILOWNER']="is the new owner of the pencil";
			myResources.content['ROOMVIEW.SQUARE']="Square";
			myResources.content['ROOMVIEW.CIRCLE']="Circle";
			myResources.content['ROOMVIEW.STRAIGHT']="Line";
			myResources.content['ROOMVIEW.FREEHAND']="Free";
			myResources.content['ROOMVIEW.COLOR']="Line Color";
			myResources.content['ROOMVIEW.FILLCOLOR']="Fill Color";
			myResources.content['ROOMVIEW.ACTFILL']="Enable fill";
			myResources.content['ROOMVIEW.BULK']="Bulk";
			myResources.content['ROOMVIEW.SAVETOPC']="Export to PC ";
			myResources.content['ROOMVIEW.UPLOADFROMPC']="Import from PC";
			myResources.content['ROOMVIEW.CLEAN']="Clear Canvas";
			myResources.content['ROOMVIEW.UNDO']="Undo";
			myResources.content['ROOMVIEW.REDO']="Redo";
			myResources.content['ROOMVIEW.PLAY']="Play";
			myResources.content['ROOMVIEW.UPLOADFROMDB']="Import of my designs";
			myResources.content['ROOMVIEW.SAVETODB']="Save to my designs";
			myResources.content['ROOMVIEW.STOPSYNCRONIZE']="Sync canvas";
			myResources.content['ROOMVIEW.STARTSYNCRONIZE']="Unsync canvas";
			myResources.content['ROOMVIEW.REQUESTPENCIL']="Request pencil";
			myResources.content['ROOMVIEW.LEFTPENCIL']="Leave pencil";
			myResources.content['ROOMVIEW.SAVECANVASTOROOM']="Link design to the room";
			myResources.content['ROOMVIEW.REMOVECANVASFROMROOM']="Remove design from room";
			
			//MANAGE USERS ROOM
			myResources.content['MANAGEUSERROOMS.SENDINVITATION']="Send Invitation";
			myResources.content['MANAGEUSERROOMS.USERSOFCOLDES']="ColDes Users";
			myResources.content['MANAGEUSERROOMS.ROLEDIT']="Edit Rol";
			myResources.content['MANAGEUSERROOMS.DELETEUSER']="Delete User";
			myResources.content['MANAGEUSERROOMS.USERSOFROOM']="Room Users";
			myResources.content['MANAGEUSERROOMS.ROL']="Rol";
			
			//MANAGE USER MESSAGES
			myResources.content['MESSAGESMANAGEMENT.ACCEPT']="Accept";
			myResources.content['MESSAGESMANAGEMENT.DECLINE']="Decline";
			myResources.content['MESSAGESMANAGEMENT.INVITATION']="Invitation";
			myResources.content['MESSAGESMANAGEMENT.INVITATIONTEXT1']="Invitation from room [";
			myResources.content['MESSAGESMANAGEMENT.INVITATIONTEXT2']="] of user (";
			myResources.content['MESSAGESMANAGEMENT.INVITATIONTEXT3']=") as \"";
			
			//POPUP ANSWERS
			myResources.content['ANSWERS.YES']="Yes";
			myResources.content['ANSWERS.NO']="No";
			myResources.content['ANSWERS.ACCEPT']="OK";
			myResources.content['ANSWERS.ENTERANDREGISTER']="Add and Enter";
			myResources.content['ANSWERS.ONLYREGISTER']="Add only";
			myResources.content['ANSWERS.CANCEL']="Cancel";
			
			
			//MESSAGE's
			myResources.content['MESSAGETITLE.LOGOUT']="LOGOUT";
			myResources.content['MESSAGE.LOGOUT']="Want to logout?";
			myResources.content['MESSAGETITLE.REGISTER']="REGISTER COMPLETE";
			myResources.content['MESSAGE.REGISTER']="User register successful.";
			myResources.content['MESSAGETITLE.UPDATE']="UPDATE COMPLETE";
			myResources.content['MESSAGE.UPDATE']="User update completed successfully.";
			myResources.content['MESSAGETITLE.REGISTERROOM']="ROOM REGISTER COMPLETE";
			myResources.content['MESSAGE.REGISTERROOM']="Room register successful.";
			myResources.content['MESSAGETITLE.UPDATERROOM']="UPDATE COMPLETE";
			myResources.content['MESSAGE.UPDATERROOM']="Room update completed successfully.";
			myResources.content['MESSAGETITLE.REMOVEUSERRELATIONOWNER']="DELETE RELATION AND ROOM";
			myResources.content['MESSAGE.REMOVEUSERRELATIONOWNER']="As the owner of the room, clear the relationship involve" + 
					" the room and remove all relationships associated with it. Are you sure?";
			myResources.content['MESSAGETITLE.REMOVEUSERRELATION']="DELETE RELATION";
			myResources.content['MESSAGE.REMOVEUSERRELATION']="Are you sure you want to remove the room relation?"
			myResources.content['MESSAGETITLE.USERROOMREGISTER']="LOG IN ROOM OPTIONS";
			myResources.content['MESSAGE.USERROOMREGISTER']="What do you want?"
			myResources.content['MESSAGETITLE.SAVEDESIGN']="SAVE DESIGN";
			myResources.content['MESSAGE.SAVEDESIGNTOUSER']="You have successfully associated with the design to the user";
			myResources.content['MESSAGE.SAVEDESIGNTOCANVAS']="You have success has been associated with the design to the room ";
			myResources.content['MESSAGETITLE.REMOVEDESIGN']="REMOVE DESIGN";
			myResources.content['MESSAGE.REMOVEDESIGN']="You have successfully deleted the associated room-design."
			myResources.content['MESSAGETITLE.SENDINVITATION']="INVITE SENT";
			myResources.content['MESSAGE.SENDINVITATION']="The invitation was sent correctly to the user."
			myResources.content['MESSAGETITLE.INVITATIONTYPE']="INVITE TYPE";
			myResources.content['MESSAGE.INVITATIONTYPE']="With what role wants to invite the user? "

			//ERROR MESSAGE's
			myResources.content['ERRORTITLE.LOGIN']="WRONG USER";
			myResources.content['ERROR.LOGIN']="The user you are trying to access is wrong, please check the fields.";
			myResources.content['ERRORTITLE.REGISTER']="REGISTER USER";
			myResources.content['ERROR.REGISTER']="The user already exist.";
			myResources.content['ERRORTITLE.REGISTERFORM']="REGISTER FORM ERROR's";
			myResources.content['ERROR.REGISTERFORM']="There are errors in the registration form, check the form fields.";
			myResources.content['ERRORTITLE.REGISTERROOM']="REGISTER ROOM";
			myResources.content['ERROR.REGISTERROOM']="The room already exist in your rooms.";
			myResources.content['ERRORTITLE.SAVEDESIGN']="ERROR IN STORAGE";
			myResources.content['ERROR.SAVEDESIGN']="Error occurred when attaching the contents of the canvas";
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