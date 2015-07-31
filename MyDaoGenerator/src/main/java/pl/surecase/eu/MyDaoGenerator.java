package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator
{

    public static Schema schema;

    public static void main(String args[]) throws Exception
    {
        schema = new Schema(3, "greendao");

        Entity deal = schema.addEntity("ObjectLink");
        deal.addIdProperty().autoincrement();
        deal.addStringProperty("url");
        deal.addStringProperty("title");

        new DaoGenerator().generateAll(schema, args[0]);
    }

}
