package ar.unrn.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
                                        //sujeto definido es el paquete colection de java
public class Proxy implements Set<Telefono> {

    private final PersonaDao personaDao;
    private final int idPersona;
    private final Set<Telefono> telefonos;

    public Proxy(PersonaDao personaDao, int idPersona) {
        this.personaDao = personaDao;
        this.idPersona = idPersona;
        this.telefonos = new HashSet<>();
    }

    private Set<Telefono> obtenerTelefonos() {
        if (this.isEmpty())
            this.telefonos.addAll(this.personaDao.obtenerTelefonosPorIdPersona(this.idPersona));
        return this.telefonos;
    }

    @Override
    public int size() {
        return telefonos.size();
    }

    @Override
    public boolean isEmpty() {
        return telefonos.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return obtenerTelefonos().contains(o);
    }

    @Override
    public Iterator<Telefono> iterator() {
        return obtenerTelefonos().iterator();
    }

    @Override
    public Object[] toArray() {
        return obtenerTelefonos().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return obtenerTelefonos().toArray(a);
    }

    @Override
    public boolean add(Telefono telefono) {
        return obtenerTelefonos().add(telefono);
    }

    @Override
    public boolean remove(Object o) {
        return obtenerTelefonos().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return obtenerTelefonos().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Telefono> c) {
        return obtenerTelefonos().addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return obtenerTelefonos().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return obtenerTelefonos().removeAll(c);
    }

    @Override
    public void clear() {
        obtenerTelefonos().clear();
    }
}
