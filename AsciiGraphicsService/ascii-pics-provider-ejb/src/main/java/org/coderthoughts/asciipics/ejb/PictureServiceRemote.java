package org.coderthoughts.asciipics.ejb;
import javax.ejb.Remote;

import org.coderthoughts.asciipics.api.PictureService;

@Remote
public interface PictureServiceRemote extends PictureService {
}
