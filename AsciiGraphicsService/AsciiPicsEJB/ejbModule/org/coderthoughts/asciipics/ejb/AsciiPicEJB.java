package org.coderthoughts.asciipics.ejb;

import javax.ejb.Stateless;

@Stateless
public class AsciiPicEJB implements PictureServiceRemote, PictureServiceLocal {
    private static final String LF = "\n";

    @Override
	public String getPic(String name) {
		return
            "         .-'''-." + LF +
            "        .~       \\" + LF +
            "     _\\/___________" + LF +
            "      (    |   |\\  |" + LF +
            "       |   |___|_|_|" + LF +
            "       |       __ |" + LF +
            "       |       \\/ |    OOOO" + LF +
            "       |          |    (  )" + LF +
            "       ========()==     | |" + LF +
            "      /        /\\  \\--;_| |" + LF +
            "     /         /|   | |__/" + LF +
            "    /_____/ __/ |   |-|" + LF +
            "     /    ~~  ) |   |" + LF +
            " ((  ~------..)/    |" + LF +
            "      |         |   |" + LF +
            "      |         |   /" + LF +
            "      \\_________|__/__" + LF +
            "       |              \\" + LF +
            "       \\               |" + LF +
            "        \\__________    |" + LF +
            "             |    /   /" + LF +
            "             |   /   /" + LF +
            "             |  /___/" + LF +
            "            /   /_/.." + LF +
            "            |  /     ~'-" + LF +
            "            |  ----..   )" + LF +
            "            |    |   ~~~" + LF +
            "            |____|" + LF +
            "             |__|" + LF +
            "             /  '-----." + LF +
            "             |________/";
    }
}
