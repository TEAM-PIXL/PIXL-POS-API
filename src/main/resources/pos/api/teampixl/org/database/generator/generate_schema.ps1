$ErrorActionPreference = "Stop"

Write-Host "Generating schema.dot from SQLite..."

$process = Start-Process -NoNewWindow -PassThru -FilePath "sqlite3.exe" `
    -ArgumentList "../pixlpos.db -init schema-generator.sql" `
    -RedirectStandardOutput "schema.dot"

$timeout = 2
$waitTime = 0
while ($process.HasExited -eq $false -and $waitTime -lt $timeout) {
    Start-Sleep -Seconds 1
    $waitTime++
}

if ($process.HasExited -eq $false) {
    Write-Host "SQLite process took too long. Killing process..."
    Stop-Process -Id $process.Id -Force
}

if (!(Test-Path "schema.dot")) {
    Write-Host "❌ Error: schema.dot was not generated. Check sqlite_errors.log."
    exit 1
}

(Get-Content schema.dot) -replace '[^\x20-\x7E]', '' | Set-Content schema_cleaned.dot

Write-Host "Generating schema.svg using GraphViz..."
dot -Tsvg schema_cleaned.dot -o schema.svg

if (!(Test-Path "schema.svg")) {
    Write-Host "❌ Error: schema.svg was not generated. Check graphviz_errors.log."
    exit 1
}

Write-Host "✅ Schema diagram successfully generated: schema.svg"

Remove-Item schema.dot 
Remove-Item schema_cleaned.dot