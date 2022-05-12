//  COMPONENT CONFIG
const component = "config"; // COMPONENT NAME 
const version = "1.0";
// ---------------------------------------
import Actions from "./Actions";
import Reducer from "./Reducer";

import Registro from "./Pages/Registro";
export default {
    component,
    version,
    Actions,
    Reducers: {
        [component + 'Reducer']: Reducer
    },
    Pages: {
        [component]: Registro,
    }
}