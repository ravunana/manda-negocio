import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { DetalheAduaneiroUpdateComponent } from 'app/entities/detalhe-aduaneiro/detalhe-aduaneiro-update.component';
import { DetalheAduaneiroService } from 'app/entities/detalhe-aduaneiro/detalhe-aduaneiro.service';
import { DetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';

describe('Component Tests', () => {
  describe('DetalheAduaneiro Management Update Component', () => {
    let comp: DetalheAduaneiroUpdateComponent;
    let fixture: ComponentFixture<DetalheAduaneiroUpdateComponent>;
    let service: DetalheAduaneiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [DetalheAduaneiroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DetalheAduaneiroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetalheAduaneiroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalheAduaneiroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetalheAduaneiro(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetalheAduaneiro();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
