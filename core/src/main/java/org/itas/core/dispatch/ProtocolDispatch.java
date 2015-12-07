package org.itas.core.dispatch;

import org.itas.core.Dispatch;
import org.itas.core.User;
import org.itas.core.net.Message;

public class ProtocolDispatch extends Dispatch {

	@Override
	public void bind(String pack) throws Exception {
		
	}

	@Override
	public void unBind() {
		
	}

	@Override
	protected Handle getHandle(short clazzHead) {
		return null;
	}

	@Override
	protected void dispatchEvent(User user, Handle event, Message message) throws Exception {
		
	}

}
