package org.coderthoughts.asciipics.ejb;

import javax.ejb.Stateless;

@Stateless
public class AsciiPicEJB implements PictureServiceRemote, PictureServiceLocal {
	@Override
	public String getPic(String name) {
		return "XXX";
	}
}
