package org.coderthoughts.asciipics.ejb;
import javax.ejb.Local;

import org.coderthoughts.asciipics.api.PictureService;

@Local
public interface PictureServiceLocal extends PictureService {
}
