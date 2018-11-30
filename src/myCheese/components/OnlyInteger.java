package myCheese.components;

import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class OnlyInteger extends PlainDocument {

	String str;

	public OnlyInteger(String str) {
		this.str = str;

	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (isValidDate(str)) {
			if (getLength() < 10) {
				super.insertString(offs, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}

		}
	}
	public boolean isValidDate(String str){  
        Pattern p = Pattern.compile("^\\d+$");  
        Matcher m = p.matcher(str);  
        return m.matches();  
    } 

}