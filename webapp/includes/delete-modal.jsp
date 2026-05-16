<!-- Bootstrap Modal for Delete Confirmation -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content border-0 shadow">
      <div class="modal-header border-bottom-0 pb-0">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center pt-0 pb-4 px-4">
        <i class="fa-solid fa-circle-exclamation text-danger mb-3" style="font-size: 3.5rem;"></i>
        <h4 class="modal-title fw-bold text-dark mb-2" id="deleteModalLabel">Confirm Deletion</h4>
        <p class="text-muted mb-4">
          Are you sure you want to permanently delete the record for <br>
          <strong id="deleteStudentName" class="text-dark fs-5">Student Name</strong>? <br>
          <span class="text-danger small">This action cannot be undone.</span>
        </p>
        <div class="d-flex justify-content-center gap-2">
          <button type="button" class="btn btn-light border px-4" data-bs-dismiss="modal">Cancel</button>
          <a href="#" id="confirmDeleteBtn" class="btn btn-danger px-4">Delete Record</a>
        </div>
      </div>
    </div>
  </div>
</div>
