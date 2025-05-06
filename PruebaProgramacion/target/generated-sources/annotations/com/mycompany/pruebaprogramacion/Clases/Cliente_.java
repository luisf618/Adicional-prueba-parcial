package com.mycompany.pruebaprogramacion.Clases;

import com.mycompany.pruebaprogramacion.Clases.Celular;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-05T22:44:27", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> apellidos;
    public static volatile SingularAttribute<Cliente, String> cedula;
    public static volatile SingularAttribute<Cliente, Celular> celular;
    public static volatile SingularAttribute<Cliente, Integer> idClie;
    public static volatile SingularAttribute<Cliente, String> nombres;

}