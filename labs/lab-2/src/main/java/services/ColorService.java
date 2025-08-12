package services;

import dao.ColorDao;
import domain.Color;

import java.util.Collection;

public class ColorService {

    private final ColorDao colorDao;

    public ColorService(ColorDao colorDao) {
        this.colorDao = colorDao;
    }

    public Color getColorById(long id) {
        return colorDao.getById(id);
    }

    public Collection<Color> getAllColors() {
        return colorDao.getAll();
    }

    public Color saveColor(Color color) {
        return colorDao.save(color);
    }

    public void deleteColorById(long id) {
        colorDao.deleteById(id);
    }

    public void deleteAllColors() {
        colorDao.deleteAll();
    }

    public void deleteColorByEntity(Color color) {
        colorDao.deleteByEntity(color);
    }

    public Color updateColor(Color color) {
        return colorDao.update(color);
    }
}
