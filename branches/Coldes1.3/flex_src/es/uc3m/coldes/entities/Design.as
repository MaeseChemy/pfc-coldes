package es.uc3m.coldes.entities
{
	import flash.utils.ByteArray;
	
		
	[Bindable]
    [RemoteClass(alias="es.uc3m.coldes.model.Design")]
	public class Design
	{
		public function Design()
		{
		}
		
		public var username:String;
		public var designcontent:ByteArray;
		public var designname:String;
	}
}