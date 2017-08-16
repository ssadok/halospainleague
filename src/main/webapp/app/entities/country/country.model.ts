import { BaseEntity } from './../../shared';

export class Country implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public latitude?: string,
        public longitude?: string,
        public players?: BaseEntity[],
    ) {
    }
}
