package es.uc3m.coldes.business
{
	import es.uc3m.coldes.entities.Room;
	import es.uc3m.coldes.entities.User;
	import es.uc3m.coldes.utils.popup.LoadingPopUp;
	import es.uc3m.coldes.utils.popup.UtilPopUp;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class RoomService
	{
		
		private var callback:Function;
		private var callbackError:Function;
		private var popUp:LoadingPopUp;
				
		public function RoomService()
		{
		}

		public function addRoom(room:Room, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultAddRoom);
			this.callback = callback;
			service.addRoom(room);
		}
		
		private function resultAddRoom(event:ResultEvent):void {
			var addResult:Number = event.result as Number;
			callback(addResult);	
		}
		
		public function registerUserRoom(user:User, room:Room, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultRegisterUserRoom);
			this.callback = callback;
			service.registerUserRoom(user, room);
		}
		
		private function resultRegisterUserRoom(event:ResultEvent):void {
			var addResult:Number = event.result as Number;
			callback(addResult);	
		}
		
		public function getUserRooms(user:User, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultGetUserRooms);
			this.callback = callback;
			service.getUserRooms(user);
		}
		
		private function resultGetUserRooms(event:ResultEvent):void {
			var usersRooms:ArrayCollection = event.result as ArrayCollection;
			callback(usersRooms);	
		}
		
		public function getColDesRooms(callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultGetColDesRooms);
			this.callback = callback;
			service.getColDesRooms();
		}
		
		private function resultGetColDesRooms(event:ResultEvent):void {
			var colDesRooms:ArrayCollection = event.result as ArrayCollection;
			callback(colDesRooms);	
		}
		
		public function createDestination(destination:String, callback:Function):void{
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultCreateDestination);
			this.callback = callback;
			service.createDestination(destination);
		}
		
		private function resultCreateDestination(event:ResultEvent):void {
			var chatId:String = event.result as String;
			callback(chatId);	
		}
		
		private function error(event:FaultEvent):void {
			UtilPopUp.showMessagePopUP("INTERNAL ERROR",
									   "There was an error performing the operation, contact your application administrator.");
			if (callbackError != null) {
				callbackError();
			}
		}
	}
}