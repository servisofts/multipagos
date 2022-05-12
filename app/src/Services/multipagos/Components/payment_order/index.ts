//  COMPONENT CONFIG
const component = "payment_order"; // COMPONENT NAME 
const version = "1.0";
// ---------------------------------------
import Actions from "./Actions";
import Reducer from "./Reducer";
// import Components from "./Components";
import Lista from "./Pages/Lista";
import Registro from "./Pages/Solicitar";


//alvaro
export default {
    component,
    version,
    Actions,
    // ...Components,
    Reducers: {
        [component + 'Reducer']: Reducer
    },
    Pages: {
        [component]: Lista,
        [component + "/registro"]: Registro,
        // [component + "/lista"]: Lista,
    }
}