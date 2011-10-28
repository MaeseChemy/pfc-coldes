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
				case 'RoomManagement': 
					if(lang == "es_ES"){
						result = "Gestión de salas";
					}else{
						result = "Room Management"
					}
					break;
				case 'MyRooms': 
					if(lang == "es_ES"){
						result = "Mis Salas";
					}else{
						result = "My Rooms"
					}
					break;
				case 'SearchRooms': 
					if(lang == "es_ES"){
						result = "Buscar Salas";
					}else{
						result = "Search Rooms"
					}
					break;
			}
			return result;
		}
		
		
		[Embed('/assets/images/icos/usersconfig.png')]
		public static const userManagement:Class;
		[Embed('/assets/images/icos/roomconfig.png')]
		public static const roomconfig:Class;
		[Embed('/assets/images/icos/myrooms.png')]
		public static const myRooms:Class;
		[Embed('/assets/images/icos/searchrooms.png')]
		public static const searchRooms:Class;
		
		public static function getModuleIcon(moduleName:String):Class {
			var result:Class;
			switch(moduleName){
				case 'UserManagement': result = userManagement;break;
				case 'RoomManagement': result = roomconfig;break;
				case 'MyRooms': result = myRooms;break;
				case 'SearchRooms': result = searchRooms;break;
				
			}
			return result;
		}

	}
}