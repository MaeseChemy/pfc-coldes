package es.uc3m.coldes.entities
{
	
	[Bindable]
    [RemoteClass(alias="es.uc3m.coldes.model.UserRoom")]
	public class UserRoom
	{
		public function UserRoom()
		{
		}
	
		public var room:Room;
		public var roomName:String;
		public var ownerUserName:String;
		public var userName:String;
		public var userfunction:Number;
		public var userfunctionDescription:String;	
	}
}