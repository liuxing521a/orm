package org.itas.common.net;


public class Message {
	
    public Message(Object obj, Object obj1) {
        setContent(obj);
        setSource(obj1);
    }
	
	private Object source;
    private Object content;
    
    public void setSource(Object obj) {
        source = obj;
    }

    public Object getContent() {
        return content;
    }

    protected void setContent(Object obj) {
        content = obj;
    }

    public Message(Object obj) {
        setContent(obj);
    }

    public Object getSource() {
        return source;
    }

}
