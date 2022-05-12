import SSocket from 'servisofts-socket';
import Parent from './index';
import Service from '../../index';

export default class Actions {
    static _getReducer = (props) => {
        return props.state[Parent.component + "Reducer"];
    }
    static getAll = (props) => {
        var reducer = Actions._getReducer(props);
        var data = reducer.data;
        if (!data) {
            if (reducer.estado == "cargando") return null;
            SSocket.send({
                component: Parent.component,
                version: Parent.version,
                type: "getAll",
                estado: "cargando",
                key_usuario: "",
            })
            return null;
        }
        return data;
    }

    static getByKey = (key, props) => {
        var data = Actions.getAll(props);
        if (!data) return null;
        return data[key];
    }
    static getByKeyServicio = (key_servicio, props) => {
        var data = Actions.getAll(props);
        if (!data) return null;
        var list = Object.values(data).filter(item => item.key_servicio == key_servicio);
        if (list.length == 0) return {};
        return list[0];
    }

    static registro = (data, props) => {
        SSocket.send({

            component: Parent.component,
            version: Parent.version,
            type: "registro",
            estado: "cargando",
            key_usuario: "",
            data: data
        })
    }
    static editar = (data, props) => {
        SSocket.send({

            component: Parent.component,
            version: Parent.version,
            type: "editar",
            estado: "cargando",
            key_usuario: "",
            data: data
        })
    }
    static eliminar = (data, props) => {
        SSocket.send({

            component: Parent.component,
            version: Parent.version,
            type: "editar",
            estado: "cargando",
            key_usuario: "",
            data: {
                ...data,
                estado: 0,
            }
        })
    }

    static getError = (type, props) => {
        if (props.state.usuarioReducer.estado == "error" && props.state.usuarioReducer.type == type) {
            props.state.usuarioReducer.estado = "";
            return props.state.usuarioReducer.error;
        }
        return null
    }

    static reboot = (data, props) => {
        SSocket.send({
            component: Parent.component,
            version: Parent.version,
            type: "reboot",
            estado: "exito",
            key_usuario: "",
            punto_venta: data
        })
    }

}