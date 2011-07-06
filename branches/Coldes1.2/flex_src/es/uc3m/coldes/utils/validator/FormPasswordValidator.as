package es.uc3m.coldes.utils.validator {
	import mx.validators.StringValidator;
	import mx.validators.ValidationResult;

	public class FormPasswordValidator extends StringValidator {
		
		public var _password1:String;
		public var _password2:String;
		
		public var _typeError:String;
		public var _error:String;
		
		public function FormPasswordValidator()	{
			super();
		}
		
		override protected function doValidation(value:Object):Array {
			var ValidatorResults:Array = new Array();
			
			ValidatorResults = super.doValidation(value);       

			if (ValidatorResults.length > 0)
			    return ValidatorResults;

			if (_password1 != _password2) {
				ValidatorResults.push(new ValidationResult(true, null, _typeError,_error));
				return ValidatorResults;
			}
			
			return ValidatorResults;
		}
	}
}