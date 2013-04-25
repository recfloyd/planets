package example.tao.groovy

import org.rec.planets.jupiter.action.Action
import org.rec.planets.jupiter.action.network.bean.Response
import org.rec.planets.jupiter.context.ActionContext
import org.rec.planets.jupiter.context.ActionContextConstants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class MockHTMLAction implements Action {

	@Override
	public void execute(ActionContext context) throws Exception {
		File f=new File(
				Thread.currentThread().getContextClassLoader().getResource("example/tao/html/source.htm").toURI())
		String text=f.getText("GBK")
		Response<String> r=new Response<String>()
		r.httpResponse=new ResponseEntity<String>(text,HttpStatus.OK)
		context.urlcontext[ActionContextConstants.KEY_HTTP_RESPONSE]=r
	}
}
