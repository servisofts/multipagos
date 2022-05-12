import { SPageListProps } from 'servisofts-component'
const ServiceName = "config";

import config from './Components/config';
import payment_order from './Components/payment_order';
const Pages: SPageListProps = {
    ...config.Pages,
    ...payment_order.Pages
}

const Reducers = {
    ...config.Reducers,
    ...payment_order.Reducers
}

export default {
    ServiceName,
    Pages,
    Reducers

};

