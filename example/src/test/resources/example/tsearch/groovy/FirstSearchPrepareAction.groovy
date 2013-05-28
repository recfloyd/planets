package example.tao.groovy

import org.rec.planets.jupiter.action.Action
import org.rec.planets.jupiter.action.network.bean.Request
import org.rec.planets.jupiter.context.ActionContext
import org.rec.planets.jupiter.context.ActionContextConstants
import org.springframework.http.HttpMethod

class FirstSearchPrepareAction implements Action {
	def urlPrefix="http://s.taobao.com/search?promote=0&sort=default&uniq=imgo&tab=all&style=list&q="
	
	public void execute(ActionContext context) throws Exception{
		String url = urlPrefix+context.crawlURL.appParams["keyword"]
		Request request = new Request()
		request.setUrl(url)
		request.setMethod(HttpMethod.GET)
		context.urlcontext[ActionContextConstants.KEY_REQUEST]=request
	}
}
