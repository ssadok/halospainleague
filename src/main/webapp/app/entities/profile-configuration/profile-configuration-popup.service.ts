import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ProfileConfiguration } from './profile-configuration.model';
import { ProfileConfigurationService } from './profile-configuration.service';

@Injectable()
export class ProfileConfigurationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private profileConfigurationService: ProfileConfigurationService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.profileConfigurationService.find(id).subscribe((profileConfiguration) => {
                    profileConfiguration.lastLogin = this.datePipe
                        .transform(profileConfiguration.lastLogin, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.profileConfigurationModalRef(component, profileConfiguration);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.profileConfigurationModalRef(component, new ProfileConfiguration());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    profileConfigurationModalRef(component: Component, profileConfiguration: ProfileConfiguration): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.profileConfiguration = profileConfiguration;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
