package tacos.web.repository;

import tacos.web.entities.Taco;

public interface IJdbcTacoRepository {
    Taco save(Taco taco);
}
