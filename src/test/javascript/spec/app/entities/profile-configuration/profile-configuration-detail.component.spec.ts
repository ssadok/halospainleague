/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProfileConfigurationDetailComponent } from '../../../../../../main/webapp/app/entities/profile-configuration/profile-configuration-detail.component';
import { ProfileConfigurationService } from '../../../../../../main/webapp/app/entities/profile-configuration/profile-configuration.service';
import { ProfileConfiguration } from '../../../../../../main/webapp/app/entities/profile-configuration/profile-configuration.model';

describe('Component Tests', () => {

    describe('ProfileConfiguration Management Detail Component', () => {
        let comp: ProfileConfigurationDetailComponent;
        let fixture: ComponentFixture<ProfileConfigurationDetailComponent>;
        let service: ProfileConfigurationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [ProfileConfigurationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProfileConfigurationService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProfileConfigurationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfileConfigurationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfileConfigurationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProfileConfiguration(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.profileConfiguration).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
