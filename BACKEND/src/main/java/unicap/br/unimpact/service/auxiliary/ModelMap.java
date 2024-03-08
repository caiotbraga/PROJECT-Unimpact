package unicap.br.unimpact.service.auxiliary;

import org.modelmapper.ModelMapper;

public class ModelMap {

    public static void map(Object source, Object destiny){
        ModelMapper mapper = new org.modelmapper.ModelMapper();
        mapper.map(source, destiny);
    }
}
