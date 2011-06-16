package es.uc3m.coldes.entities
{
	
	[Bindable]
    [RemoteClass(alias="es.uc3m.coldes.model.User")]
	public class User
	{
		public function User() {
		}
		
		public var name:String;
		public var surname1:String;
		public var surname2:String;
		public var email:String;
		public var username:String;
		public var password:String;
		public var admin:Boolean;
		public var designer:Boolean;
		public var active:Boolean;
		
	}
}