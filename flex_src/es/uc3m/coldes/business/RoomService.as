package es.uc3m.coldes.business
{
	import es.uc3m.coldes.entities.Room;
	import es.uc3m.coldes.entities.User;
	import es.uc3m.coldes.entities.UserRoom;
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
		
		public function deleteUserRoom(userRoom:UserRoom, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultDeleteUserRoom);
			this.callback = callback;
			service.deleteUserRoom(userRoom);
		}
		
		private function resultDeleteUserRoom(event:ResultEvent):void {
			var deleteResult:Number = event.result as Number;
			callback(deleteResult);
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
		
		public function enterInRoom(user:User, room:Room, callback:Function):void{
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultEnterInRoom);
			this.callback = callback;
			service.enterInRoom(user, room);
		}
		
		private function resultEnterInRoom(event:ResultEvent):void {
			var usersRoom:ArrayCollection = event.result as ArrayCollection;
			callback(usersRoom);	
		}
		
		public function notifyUserToRoom(user:User, room:Room, action:String, callback:Function):void{
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			this.callback = callback;
			service.notifyUserToRoom(user, room, action);
		}

		public function roomLogout(user:User, room:Room, totalLogout:Boolean, callback:Function):void{
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultRoomLogout);
			this.callback = callback;
			service.roomLogout(user, room, totalLogout);
		}
		
		private function resultRoomLogout(event:ResultEvent):void {
			//var usersRoom:ArrayCollection = event.result as ArrayCollection;
			//callback(usersRoom);	
		}
		
		public function getRoomUsers(room:Room, callback:Function):void{
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultGetRoomUsers);
			this.callback = callback;
			service.getRoomUsers(room);
		}
		
		private function resultGetRoomUsers(event:ResultEvent):void {
			var roomUsers:ArrayCollection = event.result as ArrayCollection;
			callback(roomUsers);	
		}

		private function error(event:FaultEvent):void {
			if (event.fault.faultString.indexOf("Session timeout") >= 0) {
				UtilPopUp.showMessagePopUP("SESSION OVER",
									   "Your session is over.");
			} else {
				UtilPopUp.showMessagePopUP("INTERNAL ERROR",
									   "There was an error performing the operation, contact your application administrator.");
			}if (callbackError != null) {
				callbackError();
			}
		}
		
		
	}
}