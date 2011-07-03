package es.uc3m.coldes.business
{
	import es.uc3m.coldes.entities.Design;
	import es.uc3m.coldes.entities.Room;
	import es.uc3m.coldes.entities.User;
	import es.uc3m.coldes.utils.popup.LoadingPopUp;
	import es.uc3m.coldes.utils.popup.UtilPopUp;
	
	import flash.utils.ByteArray;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class DesignService
	{
		private var callback:Function;
		private var callbackError:Function;
		private var popUp:LoadingPopUp;
		
		public function DesignService()
		{
		}

		public function getRoomSaveContent(room:Room, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultGetRoomSaveContent);
			this.callback = callback;
			service.getRoomSaveContent(room);
		}
		
		private function resultGetRoomSaveContent(event:ResultEvent):void {
			var content:ByteArray = event.result as ByteArray;
			callback(content);	
		}
		
		public function saveToCanvasDB(room:Room, content:ByteArray, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultSaveToCanvasDB);
			this.callback = callback;
			service.saveDesignToCanvas(room,content);
		}
		
		private function resultSaveToCanvasDB(event:ResultEvent):void {
			var result:Boolean = event.result as Boolean;
			callback(result);	
		}
		
		public function removeDesingRoom(room:Room, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultRemoveDesingRoom);
			this.callback = callback;
			service.removeDesingRoom(room);
		}
		
		private function resultRemoveDesingRoom(event:ResultEvent):void {
			var result:Boolean = event.result as Boolean;
			callback(result);	
		}
		
		public function saveToUserDB(design:Design, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultSaveToUserDB);
			this.callback = callback;
			service.saveDesignToUser(design);
		}
		
		private function resultSaveToUserDB(event:ResultEvent):void {
			var result:Boolean = event.result as Boolean;
			callback(result);	
		}
		
		public function getUserDesigns(user:User, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultGetUserDesigns);
			this.callback = callback;
			service.getUserDesigns(user);
		}
		
		private function resultGetUserDesigns(event:ResultEvent):void {
			var result:ArrayCollection = event.result as ArrayCollection;
			callback(result);	
		}
		
		public function removeUserDesign(design:Design, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultRemoveUserDesign);
			this.callback = callback;
			service.removeUserDesign(design);
		}
		
		private function resultRemoveUserDesign(event:ResultEvent):void {
			var result:Boolean = event.result as Boolean;
			callback(result);	
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