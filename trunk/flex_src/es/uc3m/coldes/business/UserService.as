package es.uc3m.coldes.business{
	
	import es.uc3m.coldes.entities.User;
	import es.uc3m.coldes.utils.popup.LoadingPopUp;
	import es.uc3m.coldes.utils.popup.UtilPopUp;
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class UserService{
		
		private var callback:Function;
		private var callbackError:Function;
		private var popUp:LoadingPopUp;

		public function UserService(){
		}
		
		public function doLogin(user:String, password:String, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultLogin);
			this.callback = callback;
			service.doLogin(user,password);
		}
		
		private function resultLogin(event:ResultEvent):void {
			var userLogedUser:User = event.result as User;
			callback(userLogedUser);
		}
		
		
		public function addUser(user:User, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultAddUser);
			this.callback = callback;
			service.addUser(user);
		}
		
		private function resultAddUser(event:ResultEvent):void {
			var newUser:String = event.result as String;
			callback(newUser);	
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