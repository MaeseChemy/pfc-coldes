package es.uc3m.coldes.module
{
	public class ModuleInfo
	{
		public function ModuleInfo()
		{
		}
		
		public static function getModuleLabel(moduleName:String, lang:String):String {
				var result:String = moduleName;
				
			switch(moduleName){
				case 'UserManagement': 
					if(lang == "es_ES"){
						result = "Gestión de usuarios";
					}else{
						result = "User Management"
					}
					break;
				case 'MyRooms': 
					if(lang == "es_ES"){
						result = "Mis Salas";
					}else{
						result = "My Rooms"
					}
					break;
			}
			return result;
		}
		
		[Embed('/assets/images/icos/usersconfig.png')]
		public static const userManagement:Class;
		[Embed('/assets/images/icos/myrooms.png')]
		public static const myRooms:Class;
		
		public static function getModuleIcon(moduleName:String):Class {
			var result:Class;
			switch(moduleName){
				case 'UserManagement': result = userManagement;break;
				case 'MyRooms': result = myRooms;break;
			}
			return result;
		}

	}
}