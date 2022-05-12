import React, { Component } from 'react';
import { connect } from 'react-redux';
import { SForm, SHr, SIcon, SNavigation, SPage, SText, SView, SLoad, SButtom, SImage, SInput } from 'servisofts-component';
import Parent from '../index';
class Registro extends Component {
    constructor(props) {
        super(props);
        this.state = {

        };
        this.key_servicio = SNavigation.getParam("key_servicio");
    }

    getForm() {
        var data = {};
        if (this.key_servicio) {
            data = Parent.Actions.getByKeyServicio(this.key_servicio, this.props);
            if (!data) return <SLoad />
        }
        return <SForm
            col={"xs-11 sm-10 md-8 lg-6 xl-4"}
            inputProps={{
                customStyle: "calistenia"
            }}
            inputs={{
                provider: { label: "provider", defaultValue: data["provider"] },
                uid: { label: "uid", defaultValue: data["uid"] },
                service_code: { label: "service_code", defaultValue: data["service_code"] },
                url: { label: "url", defaultValue: data["url"] },
                url_pago: { label: "url_pago", defaultValue: data["url_pago"] },
            }}
            onSubmitName={"registrar"}
            onSubmit={(vals) => {
                if (data.key) {
                    Parent.Actions.editar({
                        ...data,
                        ...vals
                    }, this.props)
                } else {
                    vals["key_servicio"] = this.key_servicio;
                    Parent.Actions.registro(vals, this.props);
                }

            }}
        />
    }

    render() {
        return (
            <SPage title={'Registro de ' + Parent.component} center>
                {this.getForm()}
            </SPage>
        );
    }
}
const initStates = (state) => {
    return { state }
};
export default connect(initStates)(Registro);