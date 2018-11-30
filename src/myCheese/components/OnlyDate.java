package myCheese.components;

import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
class OnlyDate extends PlainDocument {

	String str;

	public OnlyDate(String str) {
		this.str = str;

	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (isValidInteger(str)) {
			if (getLength() < 10) {
				super.insertString(offs, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}

		}
	}
	public boolean isValidInteger(String str){  
        Pattern p = Pattern.compile("^([0123]\\d)\\.([01]\\d)\\.(20\\d\\d)+$");  
        Matcher m = p.matcher(str);  
        return m.matches();  
    } 

}